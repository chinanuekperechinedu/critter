package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface PetService {
    Pet createNewPet(Pet pet);

    Pet getPetById(Long petId);

    List<Pet> getAllPets();

    List<Pet> getPetByOwnerId(long ownerId);

    List<Pet> getPetsByIds(List<Long> petIds);

}
