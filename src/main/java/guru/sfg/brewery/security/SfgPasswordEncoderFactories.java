package guru.sfg.brewery.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;

import java.util.HashMap;
import java.util.Map;

public class SfgPasswordEncoderFactories {
    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt10";
        Map<String, PasswordEncoder> encoders = new HashMap();
        encoders.put(encodingId, new BCryptPasswordEncoder(10));
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("bcrypt15", new BCryptPasswordEncoder(15));
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    private SfgPasswordEncoderFactories() {
    }
}
