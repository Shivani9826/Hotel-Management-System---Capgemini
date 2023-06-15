package com.hotel.receptionistentity;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.hotel.managerentity.AddRoom;

@Entity
@Configuration
//@ComponentScan("com.hotel")
@Table(name = "make_reservation")
public class MakeReservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id1;

	private Long id;

	private int number_of_children;

	private int number_of_adults;

	private LocalDate check_in;

	private LocalDate check_out;

	private int number_of_nights;

	private String name;

	@Column(name = "email")
	private String email;

	private String gender;

	@Column(name = "phone_number")
	private String phonenumber;

	private int age;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_rec")
	private AddRoom room_rec;

	public AddRoom getRoom_rec() {
		return room_rec;
	}

	public void setRoom_rec(AddRoom room_rec) {
		this.room_rec = room_rec;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber_of_children() {
		return number_of_children;
	}

	public void setNumber_of_children(int number_of_children) {
		this.number_of_children = number_of_children;
	}

	public int getNumber_of_adults() {
		return number_of_adults;
	}

	public void setNumber_of_adults(int number_of_adults) {
		this.number_of_adults = number_of_adults;
	}

	public LocalDate getCheck_in() {
		return check_in;
	}

	public void setCheck_in(LocalDate check_in) {
		this.check_in = check_in;
	}

	public LocalDate getCheck_out() {
		return check_out;
	}

	public void setCheck_out(LocalDate check_out) {
		this.check_out = check_out;
	}

	public int getNumber_of_nights() {
		return number_of_nights;
	}

	public void setNumber_of_nights(int number_of_nights) {
		this.number_of_nights = number_of_nights;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Long getId1() {
		return id1;
	}

	public void setId1(Long id1) {
		this.id1 = id1;
	}

	public MakeReservation(Long id1, Long id, int number_of_children, int number_of_adults, LocalDate check_in,
			LocalDate check_out, int number_of_nights, String name, String email, String gender, String phonenumber,
			int age, AddRoom room_rec) {
		super();
		this.id1 = id1;
		this.id = id;
		this.number_of_children = number_of_children;
		this.number_of_adults = number_of_adults;
		this.check_in = check_in;
		this.check_out = check_out;
		this.number_of_nights = number_of_nights;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.phonenumber = phonenumber;
		this.age = age;
		this.room_rec = room_rec;
	}

	public MakeReservation() {
		super();
		// TODO Auto-generated constructor stub
	}


}
