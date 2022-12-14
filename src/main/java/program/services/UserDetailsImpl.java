package program.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import program.entities.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private  Long id;

    private String username;
    private String name;
    private String surname;
    private String phone;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities; // колекція ролей, мачиться до GrantedAuthority

    public UserDetailsImpl(Long id,String username, String name, String surname, String phone, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username=username;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
 public  static  UserDetailsImpl build(UserEntity userEntity) {
     List<GrantedAuthority> authorities = userEntity.getRoles().stream()
             .map(role -> new SimpleGrantedAuthority(role.getName().name()))
             .collect(Collectors.toList());
     return new UserDetailsImpl(
             userEntity.getId(),
             userEntity.getUsername(),
             userEntity.getName(),
             userEntity.getSurname(),
             userEntity.getPhone(),
             userEntity.getEmail(),
             userEntity.getPassword(),
             authorities);
 }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId (){
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone(){
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
