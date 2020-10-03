package br.edu.dmos5.agenda_dmos5.model;

public class Email {
        private Integer id;
        private String dominio;
        private Contato contato;

        public Email(Integer id, String dominio, Contato contato) {
            this.id = id;
            this.dominio = dominio;
            this.contato = contato;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public void setDominio(String dominio) {
            this.dominio = dominio;
        }

        public String getDominio() {
            return dominio;
        }

        public void setContato(Contato contato) {
            this.contato = contato;
        }

        public Contato getContato() {
            return contato;
        }
}
