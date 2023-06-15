package com.hotel.receptionistcontroller;

import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hotel.managerentity.AddRoom;
import com.hotel.managerrepository.AddRoomRepository;
import com.hotel.managerservice.AddRoomService;

import com.hotel.receptionistentity.Login;
import com.hotel.receptionistentity.MakeReservation;
import com.hotel.receptionistentity.Payment;
import com.hotel.receptionistentity.Registration;
import com.hotel.receptionistentity.Search;

import com.hotel.receptionistrepository.MakeReservationRepository;
import com.hotel.receptionistrepository.RegistrationRepository;
import com.hotel.receptionistrepository.SearchRepository;

import com.hotel.receptionistservice.LoginService;
import com.hotel.receptionistservice.MakeReservationService;
import com.hotel.receptionistservice.PaymentService;
import com.hotel.receptionistservice.RegistrationServices;
import com.hotel.receptionistservice.SearchService;

@Controller
@ControllerAdvice
public class ReceptionistController {

	@Autowired
	AddRoomService service;

	@Autowired
	private RegistrationRepository registrationrepository;

	@Autowired
	private RegistrationServices registrationservice;

	@Autowired
	private LoginService loginservice;
	@Autowired
	private SearchService searchService;

	@Autowired
	private MakeReservationService makereservationservice;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private SearchRepository searchrepository;

	@Autowired
	private AddRoomService addRoomService;

	@Autowired
	private AddRoomRepository addRoomRepository;

	@Autowired
	private MakeReservationRepository makereservationrepository;

	@GetMapping("/abc")
	public String welcome() {
		return "home";
	}

	@GetMapping("/logindemo")
	public String welcome12() {
		return "login";

	}

	@GetMapping("/registerdemo")
	public String welcome1() {
		return "index";
	}

	@GetMapping("/registerss")
	public List<Registration> getAllDetails() {
		return registrationservice.getAllUser();
	}

	@PostMapping("/register")
	public String addRegistrationDetails(@ModelAttribute("user") Registration registration) {

		if (registrationrepository.findByEmail(registration.getEmail()) != null) {
			return "errr";
		} else {
			Login login = new Login();
			login.setEmail(registration.getEmail());
			login.setPassword(registration.getPassword());
			registrationservice.addDetails(registration);
			loginservice.addDetails(login);
			return "login";
		}

	}

	@PostMapping("/check")
	public String loginSubmit(@ModelAttribute Login login, Model model) {
		Registration registration = registrationrepository.findByEmail(login.getEmail());
		if (registration != null && registration.getPassword().equals(login.getPassword())
				&& registration.getEmail().equals(login.getEmail())) {
			// Successful login

			

			return "welcome";

		} else {
			// Invalid credentials
			model.addAttribute("errorMessage", "Invalid email or Password");
			return "login";
		}
	}

	@GetMapping("/afterLoginuser")
	public String afterLogin() {

		return "welcome";
	}

	@GetMapping("/searchpage")
	public String searchPage() {

		return "search";
	}

	// save the search details

	@PostMapping("/roomdetails")

	public String addSearchDetails(@ModelAttribute("abc") Search search, Model model) {

		searchService.addSearchDetails(search);

		LocalDate currentDate = LocalDate.now();
		List<AddRoom> list = service.getAllEmployees();

		model.addAttribute("employees", list);

		List<AddRoom> rooms = new ArrayList<AddRoom>();// Retrieve your rooms
		model.addAttribute("rooms", rooms);
		List<MakeReservation> mk = (List<MakeReservation>) makereservationrepository.findAll();

		List<AddRoom> notBookedRoom1 = addRoomRepository.findAvailableRooms(search.getCheck_in_date(),
				search.getCheck_out_date());

		List<AddRoom> notBookedRoom = addRoomRepository.findUnmappedRooms();
		List<AddRoom> lsmk = new ArrayList<>(notBookedRoom);

		if ((search.getCheck_in_date().isEqual(currentDate) || search.getCheck_in_date().isAfter(currentDate))
				&& search.getCheck_out_date().isAfter(currentDate)
				&& search.getCheck_in_date().isBefore(search.getCheck_out_date())
				&& search.getCheck_in_date() != null) {
			for (MakeReservation reservation : mk) {
				LocalDate date1 = reservation.getCheck_out();
				LocalDate date2 = search.getCheck_in_date();

				int comparison = date1.compareTo(date2);
				if (comparison <= 0) {

					if (!lsmk.contains(reservation.getRoom_rec())) {
						lsmk.add(reservation.getRoom_rec());
					}
				}
			}

			
			 if (notBookedRoom1.isEmpty()) {
		            model.addAttribute("noRecordFound", true);
		        } else {
		            model.addAttribute("lsmk", notBookedRoom1);
		        }
			

			return "newlistrooms";

		}
		
		model.addAttribute("errorMessage", " Please sure check in date is less than check out date.!");
		return "search";
	}

	AddRoom room = new AddRoom();
	Long room_id;
	Long id;

	// id pass as a path variable of addroom entity
	@GetMapping("/book-room/{id}")
	public String addGuest(@ModelAttribute AddRoom addroom, @PathVariable("id") Long id, Model model,
			RedirectAttributes redirectAttributes) {

		redirectAttributes.addAttribute("id", id);

		Optional<AddRoom> optionalRoom = addRoomService.findById(id);
		this.id = id;

		if (optionalRoom.isPresent()) {

			room = optionalRoom.get();
			room_id = optionalRoom.get().getRoom_id();

			return "redirect:/second";

		}

		else {

			return "err";

		}
	}

	MakeReservation makeReservation;
	long store_totalprice;
	long pricenew;

//Save the guest and reservation details
	@PostMapping("/guestreserve")
	public String AddGuestReserve(@ModelAttribute("res") MakeReservation reserve, Model model) {

		System.out.println(room_id);
		reserve.setId(room_id);
		Long newid = reserve.getId();
		System.out.println(newid);
		AddRoom addRoom = addRoomRepository.findById(id).orElse(null);
		if (addRoom != null) {
			reserve.setRoom_rec(addRoom);

		}

		makereservationservice.reservationAdd(reserve);

		long price = addRoom.getPrice();
		LocalDate l1 = reserve.getCheck_in();
		LocalDate l2 = reserve.getCheck_out();
		long numberOfDays = ChronoUnit.DAYS.between(l1, l2);

		int nights = Math.toIntExact(numberOfDays);

		long calculateprice = (price * nights);
		pricenew = calculateprice;

		// System.out.println(price);
		long gst = (calculateprice * 12) / 100;
		store_totalprice = calculateprice + gst;
		model.addAttribute("calculatedprice", store_totalprice);
		model.addAttribute("gst", gst);

		return "payment";

	}

	// Redirect get method passing through bookrooms method
	@GetMapping("/second")
	public String secondMethod(@RequestParam("id") Long id, RedirectAttributes redirectAttributes, Model model) {

		redirectAttributes.addAttribute("id", id);

		return "guestreservation";
	}

	LocalDate currentdate = LocalDate.now();
	int min = 200;
	int max = 400;

	// Save the Payment Details
	@PostMapping("/payment")
	public String AddPayment(@ModelAttribute("pay") Payment payment, Model model) {
		paymentService.addPayment(payment);
		addRoomRepository.save(room);

		List<MakeReservation> list3 = (List<MakeReservation>) makereservationrepository.findAll();
		MakeReservation m = list3.get(0);

		String name = m.getName();
		String email = m.getEmail();
		String number = m.getPhonenumber();

		model.addAttribute("names", name);
		model.addAttribute("email", email);
		model.addAttribute("number", number);

		model.addAttribute("price", pricenew);

		model.addAttribute("calculatedprice", store_totalprice);
		model.addAttribute("room_id", room_id);
		model.addAttribute("currentdate", currentdate);
		int b = (int) (Math.random() * (max - min + 1) + min);
		model.addAttribute("billnumber", b);

		model.addAttribute("invoice", payment);

		return "issuebill";
	}

	

	@GetMapping("/viewbooking")
	public String viewBookedRooms(Model model) {
		List<MakeReservation> book = makereservationservice.getAllBookings();
		model.addAttribute("book", book);

		System.out.println(book);
		return "bookedRooms";
	}

	@GetMapping("/deletes/{id1}")
	public String cancelBooking(@PathVariable("id1") Long id1) {
		makereservationservice.deleteUserById(id1);
		return "redirect:/viewbooking";
	}

}
