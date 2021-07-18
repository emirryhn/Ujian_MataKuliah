
  
package com.kuliah.main.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kuliah.main.entity.MataKuliah;
import com.kuliah.main.entity.Pertanyaan;
import com.kuliah.main.repository.PertanyaanRepository;
import com.kuliah.main.utility.FileUtility;
import com.kuliah.main.utility.MvcConfig;

import javassist.expr.NewArray;

@Controller
public class PertanyaanPage {
	
	@Autowired
	PertanyaanRepository pertanyaanRepo;
	
	@GetMapping("/pertanyaan/view")
	public String viewPertanyaan(@ModelAttribute Pertanyaan pertanyaan, Model model) {
		model.addAttribute("listpertanyaan", pertanyaanRepo.findAll());
		return "view_pertanyaan";
	}
	
	@GetMapping("/pertanyaan/add")
	public String addPertanyaanPage(Model model) {
		model.addAttribute("pertanyaannya", new Pertanyaan());
		return "add_pertanyaan";
	}
	
	@PostMapping("/pertanyaan/view")
	public String addPertanyaan(@RequestParam("pertanyaan1") String pertanyaan1, @RequestParam("jawaban1") String jawaban1,
			@RequestParam("jawaban2") String jawaban2, @RequestParam("jawaban3") String jawaban3,
			@RequestParam("jawaban4") String jawaban4,
			@RequestParam("file") MultipartFile file ,Model model) {
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Pertanyaan pertanyaan = new Pertanyaan(0, pertanyaan1, jawaban1, jawaban2, jawaban3, jawaban4, jawaban4, fileName);
		pertanyaan.setStatusGambar(fileName);
		
		String uploadDir = "./ujianMataKuliah/" + fileName;
		
		this.pertanyaanRepo.save(pertanyaan);
		
		try {
			FileUtility.saveFile(uploadDir, fileName, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		return "redirect:/pertanyaan/view";
	}
	
	@GetMapping("/pertanyaan/delete/{id}")
	public String deletePertanyaan(@PathVariable Long id, Model model) {
		this.pertanyaanRepo.deleteById(id);
		model.getAttribute("listpertanyaan");
		
		return"redirect:/pertanyaan/view";
	}
	
	@GetMapping("/pertanyaan/update/{id}")
	public String updatePertanyaan(@PathVariable Long id, Model model) {
		
		Optional<Pertanyaan> pertanyaan = pertanyaanRepo.findById(id);
		model.addAttribute("pertanyaan", pertanyaan);
		
		return "add_pertanyaan";
		
	}
}