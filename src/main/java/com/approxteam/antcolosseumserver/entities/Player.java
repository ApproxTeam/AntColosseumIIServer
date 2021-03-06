/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.approxteam.antcolosseumserver.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author adamr
 */
@Entity
@SequenceGenerator(name = "player_seq_generator", allocationSize = 20, 
initialValue = 1, sequenceName = "player_seq")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "player_seq_generator")
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String nickname;
    
    private String password;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    private List<PlayerActivation> playerActivations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PlayerActivation> getPlayerActivations() {
        return playerActivations;
    }

    public void setPlayerActivations(List<PlayerActivation> playerActivations) {
        this.playerActivations = playerActivations;
    }
    
    
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.approxteam.antcolosseumserver.db.Player[ id=" + id + " ]";
    }
    
}
