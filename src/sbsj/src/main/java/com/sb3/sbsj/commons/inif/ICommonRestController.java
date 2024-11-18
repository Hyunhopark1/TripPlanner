package com.sb3.sbsj.commons.inif;

import com.sb3.sbsj.commons.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICommonRestController<Req> extends IResponseController {
    ResponseEntity<ResponseDto> insert(Model model, @RequestBody Req dto);
    ResponseEntity<ResponseDto> update(Model model, @PathVariable Long id, @RequestBody Req dto);
    ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id);
    ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id);
}
