package com.apap.tugas1.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired
	public JabatanService jabatanService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String addJbt(Model model) {
		System.out.println("at least masuk get jabatan gitu");
		model.addAttribute("jabatan", new JabatanModel());
		return "addJabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jbt, Model model) {
		jabatanService.addJabatan(jbt);
		JabatanModel temp = jbt;
		//temp.addPegawai(null);
		//ini hubungan sama nambah pegawai juga belum
		model.addAttribute("pegawai", jbt);
		return "notifBerhasilAddData";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("id") long id, Model model) {
		JabatanModel temp = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", temp);
		return "viewJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah/{id}", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "id") long id, Model model) {
		JabatanModel temp = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", temp);
		return "updateJabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatan(@ModelAttribute JabatanModel jabatan, @RequestParam("id") Long id, Model model) {
		jabatanService.updateJabatan(id, jabatan);
		return "notifBerhasilAddData";
	}
	
	@RequestMapping(value = "/jabatan/hapus/{id}", method = RequestMethod.POST)
	public String deleteJabatan(@RequestParam("id") long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		System.out.println(jabatan.getId());
		System.out.println(jabatan.getNama());
		System.out.println(jabatan.getListPegawai());
		
		if(jabatan.getListPegawai().isEmpty()) {
			jabatanService.deleteJabatan(jabatan.getId());
			return "deleteSuccess";
		} 
		return "deleteFailed";
		
	}
	
	@RequestMapping(value = "/jabatan/viewAll", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		System.out.println("masuk sini gak");
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		return "viewAllJabatan";
	}
}
