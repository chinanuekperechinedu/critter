package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    @PersistenceContext
    EntityManager entityManager;

    private static final String FIND_PET_BY_OWNER_ID=
            "SELECT p from Pet p WHERE p.customer = :customer";

    public Pet createNewPet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId){
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()){
            return pet.get();
        } else {
            throw new ObjectNotFoundException("Pet not found!!!");
        }
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetByOwnerId(long ownerId){

        Customer customer = customerService.getCustomerById(ownerId);
        return entityManager
                .createQuery(FIND_PET_BY_OWNER_ID, Pet.class)
                .setParameter("customer", customer)
                .getResultList();
    }

    public List<Pet> getPetsByIds(List<Long> petIds){
        return petRepository.findAllById(petIds);
    }
}
