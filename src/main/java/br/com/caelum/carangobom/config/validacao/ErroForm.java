package br.com.caelum.carangobom.config.validacao;

public class ErroForm {
    private String campo;
    private String erro;

    public ErroForm(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getErro() {
        return erro;
    }
    public String getCampo() {
        return campo;
    }
}
