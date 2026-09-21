package com.projeto3.business.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TelefoneDTO {
    private long id;
    private String numero;
    private String ddd;
}
