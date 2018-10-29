package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;

public interface InstansiService {

	List<InstansiModel> getAllInstansi();
	InstansiModel getInstansiDetailById(long id);
	List<InstansiModel> getInstansiFromProvinsi(ProvinsiModel provinsi);
}
