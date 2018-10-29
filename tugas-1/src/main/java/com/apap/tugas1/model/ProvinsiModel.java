package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "provinsi")
public class ProvinsiModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "presentase_tunjangan", nullable = false)
	private double presentaseTunjangan;
	
	//karena satu provinsi bisa punya banyak instansi
	@OneToMany(mappedBy = "provinsi", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	//persist itu bertahan.. jadi kalo didelete, dia ga ke delete kitu??
	@JsonIgnore
	private List<InstansiModel> daftarInstansi;
	//apa itu fetch type? ada dua jenis. lazy dan eager

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public double getPresentasiTunjangan() {
		return presentaseTunjangan;
	}

	public void setPresentasiTunjangan(double presentasiTunjangan) {
		this.presentaseTunjangan = presentasiTunjangan;
	}

	public List<InstansiModel> getDaftarInstansi() {
		return daftarInstansi;
	}

	public void setDaftarInstansi(List<InstansiModel> daftarInstansi) {
		this.daftarInstansi = daftarInstansi;
	}
	
	
}
