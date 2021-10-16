package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertToPetEntity(petDTO);
        Pet newPet = petService.createNewPet(pet);

        PetDTO newPetDTO = convertToPetDTO(newPet);
        return newPetDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return convertToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets()
                .stream()
                .map(this::convertToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetByOwnerId(ownerId)
                .stream()
                .map(this::convertToPetDTO)
                .collect(Collectors.toList());
    }

    private Pet convertToPetEntity(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setBirthDate(petDTO.getBirthDate());

        Customer customer = petDTO.getOwnerId() != 0 ? customerService.getCustomerById(petDTO.getOwnerId()) : null;
        pet.setCustomer(customer);

        return pet;
    }

    private PetDTO convertToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setBirthDate(pet.getBirthDate());

        Long ownerId = pet.getCustomer() != null ? pet.getCustomer().getId(): 0;
        petDTO.setOwnerId(ownerId);

        return petDTO;
    }
}
