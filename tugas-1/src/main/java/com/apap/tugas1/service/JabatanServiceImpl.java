package com.apap.tugas1.service;

import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	@Autowired
	private JabatanDB jabatanDb;
	
	@Override
	public JabatanModel getJabatanDetailByNama(String nama) {
		return jabatanDb.findByNama(nama);
	}
	
	@Override
	public void addJabatan(JabatanModel jbt) {
		jabatanDb.save(jbt);
	}
	
	@Override
	public void deleteJabatan(long id) {
		JabatanModel temp = jabatanDb.findById(id);
		jabatanDb.delete(temp);
	}
	
	@Override
	public List<JabatanModel> getAllJabatan(){
		return jabatanDb.findAll();
	}
	
	@Override
	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id);
	}
	
	@Override
	public void updateJabatan(long id, JabatanModel jabatan) {
		JabatanModel temp = jabatanDb.findById(id);
		temp.setNama(jabatan.getNama());
		temp.setDeskripsi(jabatan.getDeskripsi());
		temp.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(temp);
	}
	
}
