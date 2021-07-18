package com.kuliah.main.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kuliah.main.entity.MataKuliah;
import com.kuliah.main.repository.MataKuliahRepository;

@Controller
public class MataKuliahPage {
	
	@Autowired
	MataKuliahRepository mataKuliahRepo;
	
	
	@GetMapping("/matakuliah/view")
	public String viewIndexMataKuliah(Model model) {
		
		model.addAttribute("listMataKuliah",mataKuliahRepo.findAll());
		model.addAttribute("active",3);
		return "view_matakuliah.html";
	}
	
	
	@GetMapping("/matakuliah/add")
	public String viewAddMataKuliah(Model model) {
		
		// buat penampung data MataKuliah di halaman htmlnya
		model.addAttribute("matakuliah",new MataKuliah());
		
		return "add_matakuliah.html";
	}
	
	@PostMapping("/matakuliah/view")
	  public String addMataKuliah(@ModelAttribute MataKuliah MataKuliah, Model model) {
		
		// buat penampung data MataKuliah di halaman htmlnya
		this.mataKuliahRepo.save(MataKuliah);
		model.addAttribute("listMataKuliah",mataKuliahRepo.findAll());
		
		
		return "redirect:/matakuliah/view";
	}
	
	
	@GetMapping("/matakuliah/update/{id}")
	public String viewUpdateMataKuliah(@PathVariable Long id, Model model) {
		
		Optional<MataKuliah> MataKuliah = mataKuliahRepo.findById(id);
		// buat penampung data MataKuliah di halaman htmlnya
		model.addAttribute("matakuliah",MataKuliah);
		
		return "add_matakuliah";
	}
	
	@GetMapping("/matakuliah/delete/{id}")
	public String deleteMataKuliah(@PathVariable Long id, Model model) {
		
		this.mataKuliahRepo.deleteById(id);
		model.getAttribute("listMataKuliah");
		
		return "redirect:/matakuliah/view";
	}

}
