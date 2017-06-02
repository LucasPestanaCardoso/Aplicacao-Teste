package br.com.cadastropessoa.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.cadastropessoa.model.Relatorio;

public class ArquivoResource extends ResponseEntity<byte[]> {

    public ArquivoResource(Relatorio relatorio) {
        super(relatorio.getConteudo(), buildHTTPHHeader(relatorio), HttpStatus.OK);
    }

    private static HttpHeaders buildHTTPHHeader(final Relatorio relatorio) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentLength(relatorio.getConteudo().length);
        httpHeaders.set("content-disposition", "attachment; filename=" + relatorio.getNome() + ".xlsx");
        return httpHeaders;
    }
}