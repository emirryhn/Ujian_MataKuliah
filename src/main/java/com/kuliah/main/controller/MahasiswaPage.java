package com.kuliah.main.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.kuliah.main.entity.Mahasiswa;
import com.kuliah.main.repository.MahasiswaRepository;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class MahasiswaPage {
	
	@Autowired
	MahasiswaRepository mahasiswaRepo;
	
	@GetMapping("/mahasiswa/view")
	public String viewMahasiswa(Model model) {
		model.addAttribute("listMahasiswa", mahasiswaRepo.findAll());
		return "view_mahasiswa.html";
	}
	
	@GetMapping("/mahasiswa/add/")
	public String addMahasiswaPage(Model model) {
		model.addAttribute("mahasiswa", new Mahasiswa());
		return "add_mahasiswa.html";
	}
	
	@PostMapping("/mahasiswa/add")
	public String addMahasiswa(@ModelAttribute Mahasiswa mahasiswa) {
		BCryptPasswordEncoder passEnc = new BCryptPasswordEncoder();
		String newPass = passEnc.encode(mahasiswa.getPassword());
		mahasiswa.setPassword(newPass);
		mahasiswaRepo.save(mahasiswa);
		return "redirect:/mahasiswa/view";
	}
	
	//gabisa delete karena reference table
	@GetMapping("/mahasiswa/delete/{id}")
	public String deleteMahasiswa(@PathVariable Long id, Model model) {
		this.mahasiswaRepo.deleteById(id);
		model.getAttribute("listMahasiswa");
		return "redirect:/mahasiswa/view";
	}
	
	@GetMapping("/mahasiswa/update/{id}")
	public String updateMahasiswa(@PathVariable Long id, Model model) {
		
		Optional<Mahasiswa> mahasiswa = this.mahasiswaRepo.findById(id);
		model.addAttribute("mahasiswa", mahasiswa);
		
		return"/mahasiswa/add";
	}
	
}
