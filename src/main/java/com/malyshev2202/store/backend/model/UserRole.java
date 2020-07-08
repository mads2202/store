package com.malyshev2202.store.backend.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;


public enum  UserRole  {
    USER,ADMIN;
}
