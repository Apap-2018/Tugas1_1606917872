package com.apap.tugas1.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	List<PegawaiModel> viewAllPegawai();
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	void addPegawai(PegawaiModel pegawai);
	void deletePegawai(String nip);
	void updatePegawai(PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir, String tahunMasuk);
	List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
    List<PegawaiModel> findByInstansi(InstansiModel instansi);
}
