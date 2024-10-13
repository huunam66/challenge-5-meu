import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class LocalStorageService {

  private secretKey: string = '9-udx#5pt&n%ulbg+_cc05t)NguyenHuuNam(6602)725vcvmqj^vl+34p)ywly)s-)h';

  constructor() { }

  public setData(key: string, value: string) {

    if(typeof localStorage == 'undefined') return;

    localStorage?.setItem(key, this.encrypt(value));
  }

  public getData(key: string) {

    if(typeof localStorage == 'undefined') return null;

    let data = localStorage?.getItem(key)||null;

    if(!data) return data;


    return this.decrypt(data);
  }
  public removeData(key: string) {

    if(typeof localStorage == 'undefined') return;

    localStorage?.removeItem(key);
  }

  public clearData() {

    if(typeof localStorage == 'undefined') return;

    localStorage?.clear();
  }

  private encrypt(txt: string): string {
    return CryptoJS.AES.encrypt(txt, this.secretKey).toString();
  }

  private decrypt(txtToDecrypt: string) {
    return CryptoJS.AES.decrypt(txtToDecrypt, this.secretKey).toString(CryptoJS.enc.Utf8);
  }
}
