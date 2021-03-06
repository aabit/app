/**
 * Implementation of the Organization controller
 */
package com.example.demo_addFunc.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo_addFunc.exception.RecordNotFoundException;
import com.example.demo_addFunc.model.OrganizationEntity;
import com.example.demo_addFunc.service.OrganizationService;

@Controller
@RequestMapping("/org")
public class OrganizationMvcController {
    
	@Autowired
	OrganizationService service;

	@RequestMapping
	public String getAllOrganizations(Model model) 
	{
		List<OrganizationEntity> list = service.getAllOrganizations();

		model.addAttribute("organizations", list);
		return "list-organizations";
	}

	/**
	 * Edits the Organizaton
	 * <p>
	 * Selects the organization by id then presents the values for editing
	 * <p>
	 * @param model
	 * @param id id of the organization to retrieve for editing
	 * @return add-edit-organization html resource template
	 * @throws RecordNotFoundException
	 */
	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editOrganizationById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			OrganizationEntity entity = service.getOrganizationById(id.get());
			model.addAttribute("organization", entity);
		} else {
			model.addAttribute("organization", new OrganizationEntity());
		}
		return "add-edit-organization";
	}
	
	/**
	 * Delete the organization by id
	 * <p>
	 * @param model
	 * @param id unique id of the organization to delete
	 * @return Redirects to list of organizations
	 * @throws RecordNotFoundException
	 */
	@RequestMapping(path = "/delete/{id}")
	public String deleteOrganizationById(Model model, @PathVariable("id") Long id) 
							throws RecordNotFoundException 
	{
		service.deleteOrganizationById(id);
		return "redirect:/";
	}

	/**
	 * Create a new organization
	 * <p>
	 * @param opportunity
	 * @return Redirects to list of organizations
	 */
	@RequestMapping(path = "/createOrganization", method = RequestMethod.POST)
	public String createOrUpdateOrganization(OrganizationEntity organization) 
	{
		service.createOrUpdateOrganization(organization);
		return "redirect:/org";
	}
}