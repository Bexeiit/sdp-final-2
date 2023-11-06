package Decorator;

import java.util.Scanner;

public class UserRemoteSystem {
    public void userRemote() {
        Scanner cin = new Scanner(System.in);
        boolean isOff = true;

        while(isOff) {
            System.out.println("""
                    Choose option:
                    1. My Flight List
                    2. Purchase Tickets""");
        }
    }
}
