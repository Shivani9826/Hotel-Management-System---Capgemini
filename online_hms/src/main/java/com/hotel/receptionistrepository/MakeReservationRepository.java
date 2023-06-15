package com.hotel.receptionistrepository;




import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotel.receptionistentity.MakeReservation;




@Repository
public interface MakeReservationRepository extends CrudRepository<MakeReservation, Long> {

	void deleteById(Long id1);

	/* Optional<MakeReservation> findById(int id); */

	

}