package examples.boot.myshop;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncodeTest {
    @Test
    public void encodingTest() throws Exception{
        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories
                        .createDelegatingPasswordEncoder();

        // {bcrypt}$2a$10$x77kC7Q4LEBRCvmeM2fifu8yYx5jew.gvdg4af/DnI1Ycvvwk8csu
        // {bcrypt}$2a$10$FAqkGi0dk3GCI7n/Rg7zFej7rJa3F7ZfuLHikGs.J1OsKUU3YF1j6

        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);

        boolean matches = passwordEncoder.matches("1234",
                "{bcrypt}$2a$10$0IKjNgE8fn.5oTSc4V0Cj.9NArYsSZYEZl7NVwV/cPP27dKDOGy76");
        System.out.println(matches);
        Assert.assertTrue(matches);
    }
}
