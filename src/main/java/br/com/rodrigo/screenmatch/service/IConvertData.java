package br.com.rodrigo.screenmatch.service;

public interface IConvertData {
    <T> T getData(String json, Class<T> tClass);
}
