package com.ndt.capstone.service.contract;



public interface AccountService {
    void lockAccount(Long id);


    void unlockAccount(Long id);
}
