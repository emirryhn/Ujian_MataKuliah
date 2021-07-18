package com.kuliah.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kuliah.main.entity.Dosen;
import com.kuliah.main.repository.DosenRepository;

@Controller
public class DosenPage {
	
	@Autowired
	DosenRepository dosenRepo;
	
	@GetMapping("/dosen/view")
	public String viewDosen(Model model) {
		model.addAttribute("listDosen", dosenRepo.findAll());
		return "view_dosen.html";
	}
	
	@GetMapping("/dosen/add")
	public String addDosenPage(Model model) {
		model.addAttribute("dosen", new Dosen());
		return "add_dosen.html";
	}
	
	@PostMapping("/dosen/add")
	public String addDosen(@ModelAttribute Dosen dosen) {
		
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		String newPass = passEncoder.encode(dosen.getPassword());
		dosen.setPassword(newPass);
		dosenRepo.save(dosen);
		return "redirect:/dosen/view";
		
	}
	
	@GetMapping("/dosen/delete/{id}")
	public String deleteDosen(@PathVariable Long id, Model model) {
		this.dosenRepo.deleteById(id);
		model.getAttribute("ListDosen");
		return"redirect:/dosen/view";
	}
	
	@GetMapping("/dosen/update/{id}")
	public String udpateDosen(@PathVariable Long id, Model model) {
		
		Optional<Dosen> dosen = dosenRepo.findById(id);
		model.addAttribute("dosen", dosen);
		
		return "add_dosen";
		
	}

}
