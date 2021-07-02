CREATE TABLE usuario
(
    id              BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
    nome            VARCHAR(50) NOT NULL,
    cpf             VARCHAR(15) NOT NULL,
    data_nascimento DATE,
    data_cadastro   DATE        NOT NULL,
    sexo            VARCHAR(1),
    id_cargo        BIGINT(10)  NOT NULL,
    FOREIGN KEY (id_cargo) REFERENCES cargo(id),
    UNIQUE (cpf)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE usuario_perfil (
    id_usuario BIGINT(10) NOT NULL,
    id_perfil BIGINT(10) NOT NULL,
    PRIMARY KEY (id_usuario,id_perfil),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_perfil) REFERENCES perfil(id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;