package com.apap.tugas1.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		InstansiModel instansi = pegawai.getInstansi();
        Date tanggalLahir = pegawai.getTanggalLahir();
        String tahunMasuk = pegawai.getTahunMasuk();
        int pegawaiKe = 1;

        List<PegawaiModel> listPegawaiNIPMirip = this.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
        if (!listPegawaiNIPMirip.isEmpty()) {
            pegawaiKe = (int) (Long.parseLong(listPegawaiNIPMirip.get(listPegawaiNIPMirip.size()-1).getNip())%100) + 1;
        }

        String kodeInstansi = instansi.getId().toString();

        String pattern = "dd-MM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
        String pegawaiKeString = pegawaiKe/10 == 0 ? ("0" + Integer.toString(pegawaiKe)) : (Integer.toString(pegawaiKe));
        String nip = kodeInstansi + tanggalLahirString + tahunMasuk + pegawaiKeString;

        System.out.println("nip adalaaaahhh "+nip);
        
        pegawai.setNip(nip);
        pegawaiDb.save(pegawai);
	}
	
	@Override
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi,
			Date tanggalLahir, String tahunMasuk) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tahunMasuk, tanggalLahir);
	}
	
	@Override
	public void deletePegawai(String nip) {
		PegawaiModel temp = pegawaiDb.findByNip(nip);
		pegawaiDb.delete(temp);
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		//pokoknya ini masih banyak banget salaah
		PegawaiModel temp = new PegawaiModel();
		PegawaiModel temp2 = new PegawaiModel();
		temp.setNama(pegawai.getNama());
		temp.setTahunMasuk(pegawai.getTahunMasuk());
		temp.setTempatLahir(pegawai.getTempatLahir());
		temp.setTanggalLahir(pegawai.getTanggalLahir());
		//temp.setNip(); //masih salaaaaah
		temp2 = pegawaiDb.findByNip(pegawai.getNip());
		pegawaiDb.save(temp);
	}
	
	@Override
	public List<PegawaiModel> viewAllPegawai() {
		return pegawaiDb.findAll();
	}
	
	@Override
	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi){
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}
	
	@Override
	public List<PegawaiModel> findByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
	    return pegawaiDb.findByInstansi(instansi);
	}

	@Override
    public List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
        // TODO Auto-generated method stub
        List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
        List<PegawaiModel> listPegawaiInstansi = pegawaiDb.findByInstansi(instansi);

        for (int i = 0; i < listPegawaiInstansi.size(); i++) {
            int sizeJ = listPegawaiInstansi.get(i).getListJabatan().size();
            for (int j = 0; j < sizeJ; j++ ) {
                if (listPegawaiInstansi.get(i).getJabatanPegawaiList().get(j).getJabatan().getId() == jabatan.getId()) {
                    hasil.add(listPegawaiInstansi.get(i));
                }
            }

        }
        return hasil;
    }
}
