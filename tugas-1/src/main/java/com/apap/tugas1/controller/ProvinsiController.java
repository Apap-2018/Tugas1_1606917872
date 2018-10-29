package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class ProvinsiController {
	
	 @Autowired
	    private ProvinsiService provinsiService;
	 
	 @Autowired
	    private InstansiService instansiService;
	 
	    @RequestMapping(value = "/provinsi/instansi", method = RequestMethod.GET)
	    @ResponseBody
	    public List<InstansiModel> findAllInstansi(@RequestParam (value = "provinsiId", required = true) long id) {
	    	System.out.println("sampai di provinsi ctrl ");
	    	ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(id);
	        
	        System.out.println(provinsi.getDaftarInstansi());
	        return instansiService.getInstansiFromProvinsi(provinsi);
	    }
	    
}
