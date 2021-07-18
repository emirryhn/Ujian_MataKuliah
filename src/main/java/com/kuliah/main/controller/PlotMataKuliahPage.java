package com.kuliah.main.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuliah.main.entity.LembarPenilaian;
import com.kuliah.main.entity.MataKuliah;
import com.kuliah.main.entity.Pertanyaan;
import com.kuliah.main.entity.PlotMataKuliah;
import com.kuliah.main.entity.Soal;
import com.kuliah.main.entity.UjianHasil;
import com.kuliah.main.repository.DosenRepository;
import com.kuliah.main.repository.MahasiswaRepository;
import com.kuliah.main.repository.MataKuliahRepository;
import com.kuliah.main.repository.PlotMataKuliahRepository;
import com.kuliah.main.repository.SoalRepository;

import lombok.Getter;

@Controller
public class PlotMataKuliahPage {
	
	@Autowired
	PlotMataKuliahRepository plotMatkulRepo;
	@Autowired
	MataKuliahRepository mataKuliahRepo;
	@Autowired
	MahasiswaRepository mahasiswaRepo;
	@Autowired
	DosenRepository dosenRepo;
	@Autowired
	SoalRepository soalRepo;
	
	
	@GetMapping("/plotmatakuliah/view")
	public String viewPlotMataKuliah(Model model) {
		model.addAttribute("listPlotMataKuliah", plotMatkulRepo.findAll());
		return "view_plotmatakuliah.html";
	}
	
	@GetMapping("/plotmatakuliah/add")
	public String addPlotMataKuliahPage(Model model) {
		model.addAttribute("plotmatkul", new PlotMataKuliah());
		model.addAttribute("listMataKuliah", mataKuliahRepo.findAll());
		model.addAttribute("listMahasiswa", mahasiswaRepo.findAll());
		model.addAttribute("listDosen", dosenRepo.findAll());
		model.addAttribute("listSoal", soalRepo.findAll());
		return "add_plotmatakuliah.html";
	}
	
	@PostMapping("/plotmatakuliah/add")
	public String addPlotmatakuliah(@ModelAttribute PlotMataKuliah plotMatkul) {
		plotMatkulRepo.save(plotMatkul);
		return "redirect:/plotmatakuliah/view";
	}
	
	//tidak bisa delete karena reference table
	@GetMapping("/plotmatakuliah/delete/{id}")
	public String deleteplotmatkul(@PathVariable Long id, Model model) {
		this.plotMatkulRepo.deleteById(null);
		model.getAttribute("lostPlotMataKuliah");
		return"";
	}
	
	@GetMapping("/plotmatakuliah/update/{id}")
	public String updatePlotmatkul(@PathVariable Long id, Model model) {
		
		Optional<PlotMataKuliah> plotMatkul = plotMatkulRepo.findById(id);
		model.addAttribute("plotmatkul", plotMatkul);
		model.addAttribute("listMataKuliah", mataKuliahRepo.findAll());
		model.addAttribute("listMahasiswa", mahasiswaRepo.findAll());
		model.addAttribute("listDosen", dosenRepo.findAll());
		model.addAttribute("listSoal", soalRepo.findAll());
		return"add_plotmatakuliah";
		
	}
	
}
