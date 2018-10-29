package com.apap.tugas1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jabatan")
public class JabatanModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable = false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "deskripsi", nullable = false)
	private String deskripsi;
	
	@NotNull
	@Column(name = "gaji_pokok", nullable = false)
	private double gajiPokok;
	
	@ManyToMany(fetch = FetchType.LAZY,  cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
	@JoinTable(name = "jabatan_pegawai",
    joinColumns = { @JoinColumn(name = "id_jabatan") },
    inverseJoinColumns = { @JoinColumn(name = "id_pegawai") })
	@JsonIgnore
	private List<PegawaiModel> listPegawai;
	
	@OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<JabatanPegawaiModel> jabatanPegawaiList;

    public List<JabatanPegawaiModel> getJabatanPegawaiList() {
        return jabatanPegawaiList;
    }

    public void setJabatanPegawaiList(List<JabatanPegawaiModel> jabatanPegawaiList) {
        this.jabatanPegawaiList = jabatanPegawaiList;
    }
	
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	private PegawaiModel pegawai;

	
	public long getId() {
		return id;
	}

	
	public int getJumlahPegawai() {
		return listPegawai.size();
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public PegawaiModel getPegawai() {
		return pegawai;
	}

	public void setPegawai(PegawaiModel pegawai) {
		this.pegawai = pegawai;
	}
	
	public void addPegawai(PegawaiModel pgw) {
		this.listPegawai.add(pgw);
		pgw.getListJabatan().add(this);
	}
	
	public void deletePegawai(PegawaiModel pgw) {
		this.listPegawai.remove(pgw);
		pgw.getListJabatan().remove(this);
	}

	public List<PegawaiModel> getListPegawai() {
		return listPegawai;
	}

	public void setListPegawai(List<PegawaiModel> listPegawai) {
		this.listPegawai = listPegawai;
	}

	
}
