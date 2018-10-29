package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {

	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value= "/", method = RequestMethod.GET)
	private String home(Model model) {
		List<JabatanModel> tempJabatan = jabatanService.getAllJabatan();
		List<InstansiModel> tempInstansi = instansiService.getAllInstansi();
		model.addAttribute("jbt", tempJabatan);
		model.addAttribute("instansi", tempInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		JabatanModel jabatan = new JabatanModel();
		
		ArrayList<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
        listJabatan.add(jabatan);
        pegawai.setListJabatan(listJabatan);
        
        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();
        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<InstansiModel> listInstansi = instansiService.getAllInstansi();

        System.out.println("apakah kosong list all province kosong? " + listAllProvinsi.size());
        System.out.println("apakah kosong list all jabatan kosong? " + listAllJabatan.size());
        
        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        model.addAttribute("listInstansi", listInstansi);
        
		return "addPegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
    public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
        if(pegawai.getJabatan() == null) {
            pegawai.setListJabatan(new ArrayList<JabatanModel>());
        }

        pegawai.getListJabatan().add(new JabatanModel());

        System.out.println("apakah masuk ke add row?");
        
        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();

        model.addAttribute("pegawai", pegawai);
        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        return "addPegawai";
    }

    @RequestMapping(value="/pegawai/tambah", params={"removeRow"}, method = RequestMethod.POST)
    public String removeRow(@ModelAttribute PegawaiModel pegawai, final BindingResult bindingResult, final HttpServletRequest req, Model model) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        pegawai.getListJabatan().remove(rowId.intValue());

        model.addAttribute("pegawai", pegawai);

        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();

        model.addAttribute("listProvinsi", listAllProvinsi);
        model.addAttribute("listJabatan", listAllJabatan);
        return "addPegawai";
    }
    
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println("Udah masuk post");
        pegawaiService.addPegawai(pegawai);
        System.out.println(pegawai.getNama()+" "+pegawai.getJabatan());
        System.out.println(pegawai.getNama()+" "+pegawai.getInstansi());
        System.out.println("nyangkut kok");
        model.addAttribute("pegawai", pegawai);
		return "addPegawaiSuccess";
	}
	
	@RequestMapping(value = "/pegawai/view", method = RequestMethod.GET)
	public String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<JabatanModel> listJabatan = pegawai.getListJabatan();
		
		double gajiBesar = listJabatan.get(0).getGajiPokok();
		
		for(JabatanModel temp : listJabatan) {
			if( gajiBesar<temp.getGajiPokok() ) {
				gajiBesar = temp.getGajiPokok();
			}
		}
		
		double tunjangan = pegawai.getInstansi().getProvinsi().getPresentasiTunjangan();
		double gaji = gajiBesar + (tunjangan/100 * gajiBesar);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listJabatan", listJabatan); 
		model.addAttribute("gaji" , gaji);
		return "viewPegawai";
	}
	
	
	@RequestMapping(value = "/pegawai/tertua-termuda", method = RequestMethod.GET)
	public String viewPegawaiTuaMuda(@RequestParam("id") long id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(id);
		System.out.println(instansi.getDeskripsi() + " masuk pegawai teertua termuda");
		List<PegawaiModel> listPegawai = pegawaiService.findByInstansiOrderByTanggalLahirAsc(instansi);
		
		PegawaiModel tertua = listPegawai.get(0);
		PegawaiModel termuda = listPegawai.get(listPegawai.size()-1);
		List<JabatanModel> jabatanPegawaiTertua = tertua.getListJabatan();
		List<JabatanModel> jabatanPegawaiTermuda = termuda.getListJabatan();
		
		System.out.println(tertua.getNama());
		System.out.println(termuda.getNama());
		
		model.addAttribute("tua", tertua);
		model.addAttribute("muda", termuda);
		model.addAttribute("jabatanPegawaiTertua", jabatanPegawaiTertua);
		model.addAttribute("jabatanPegawaiTermuda", jabatanPegawaiTermuda);
		return "viewPegawaiTertuaTermuda";
	}
	
	
	 @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	    public String searchPegawai(Model model) {

	        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
	        List<InstansiModel> listInstansi = instansiService.getAllInstansi();
	        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
	        
	        model.addAttribute("listProvinsi", listProvinsi);
	        model.addAttribute("listInstansi", listInstansi);
	        model.addAttribute("listJabatan", listJabatan);

	        return "searchPegawai";
	    }

	    @RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params= {"search"})
	    public String searchPegawaiSubmit(@RequestParam(value="idProvinsi", required=false) Optional<Long> idProvinsi,
                @RequestParam(value="idInstansi", required=false) Optional<Long> idInstansi,
                @RequestParam(value="idJabatan", required=false) Optional<Long> idJabatan, Model model) {

	    	
	        List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
	        if (idProvinsi.isPresent()) {
	            if (idInstansi.isPresent()) {
	                if (idJabatan.isPresent()) {
	                    InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi.get());
	                    JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan.get());

	                    listPegawai = pegawaiService.findByInstansiAndJabatan(instansi, jabatan);

	                }
	                else {
	                    InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi.get());
	                    listPegawai = pegawaiService.findByInstansi(instansi);
	                }

	            }
	            else {
	                if (idJabatan.isPresent()) {
	                    List<InstansiModel> listInstansi = provinsiService.getProvinsiDetailById(idProvinsi.get()).getDaftarInstansi();

	                    for (int i = 0; i < listInstansi.size(); i++) {
	                        List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getDaftarPegawai();
	                        for (int j = 0; j < listPegawaiBaru.size(); j++) {
	                            for (int k = 0; k < listPegawaiBaru.get(j).getJabatanPegawaiList().size(); k++) {
	                                if (listPegawaiBaru.get(j).getJabatanPegawaiList().get(k).getJabatan().getId() == idJabatan.get()) {
	                                    listPegawai.add(listPegawaiBaru.get(j));
	                                    break;
	                                }
	                            }

	                        }
	                    }


	                }
	                else {
	                    List<InstansiModel> listInstansi = provinsiService.getProvinsiDetailById(idProvinsi.get()).getDaftarInstansi();
	                    for (int i = 0; i < listInstansi.size(); i++) {
	                        List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getDaftarPegawai();
	                        listPegawai.addAll(listPegawaiBaru);
	                    }
	                }
	            }
	        }
	        else {
	            if (idJabatan.isPresent()) {
	                JabatanModel jabatan = jabatanService.getJabatanDetailById(idJabatan.get());
	                for (int i = 0; i< jabatan.getListPegawai().size(); i++) {
	                    listPegawai.add(jabatan.getJabatanPegawaiList().get(i).getPegawai());
	                }
	            } 
	            
	            if (idInstansi.isPresent()) {
	            	InstansiModel instansi = instansiService.getInstansiDetailById(idInstansi.get());
                    listPegawai = pegawaiService.findByInstansi(instansi);
	            }
	        }

	        List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
	        List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
	        listInstansi = listProvinsi.get(0).getDaftarInstansi();
	        List<JabatanModel> listJabatan = jabatanService.getAllJabatan();

	        model.addAttribute("title", "Cari Pegawai");
	        model.addAttribute("listProvinsi", listProvinsi);
	        model.addAttribute("listInstansi", listInstansi);
	        model.addAttribute("listJabatan", listJabatan);
	        model.addAttribute("listPegawai", listPegawai);
	        return "searchPegawai";
	    }
	    
	    @RequestMapping(value = "/pegawai/ubah/{id}", method = RequestMethod.GET)
		private String updatePegawai(@RequestParam(value = "id") long id, Model model) {
			PegawaiModel pegawai = new PegawaiModel();
			JabatanModel jabatan = new JabatanModel();
			
			ArrayList<JabatanModel> listJabatan = new ArrayList<JabatanModel>();
	        listJabatan.add(jabatan);
	        pegawai.setListJabatan(listJabatan);
	        
	        List<ProvinsiModel> listAllProvinsi = provinsiService.getAllProvinsi();
	        List<JabatanModel> listAllJabatan = jabatanService.getAllJabatan();
	        List<InstansiModel> listInstansi = instansiService.getAllInstansi();

	        System.out.println("apakah kosong list all province kosong? " + listAllProvinsi.size());
	        System.out.println("apakah kosong list all jabatan kosong? " + listAllJabatan.size());
	        
	        model.addAttribute("pegawai", pegawai);
	        model.addAttribute("listProvinsi", listAllProvinsi);
	        model.addAttribute("listJabatan", listAllJabatan);
	        model.addAttribute("listInstansi", listInstansi);
	        
			return "updatePegawai";
		}
}
