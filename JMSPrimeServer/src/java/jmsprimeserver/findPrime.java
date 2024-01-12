/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmsprimeserver;

/**
 *
 * @author Tong
 */
public class findPrime {
    private boolean isPrime(int n){
        int i;
        for(i = 2; i * i <= n; i++){
            if((n % i) == 0){
                return false;
            }
        }
        return true;
    }
    
    protected int countPrimeWithRange(int a, int b){
        int countPrime = 0;
        for(int i = a; i <= b; i++){
            if(isPrime(i)){
                countPrime++;
            }
        }
        return countPrime;
    }
}
