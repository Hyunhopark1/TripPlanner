package com.sb3.sbsj.user;

import com.sb3.sbsj.commons.dto.CUDInfoDto;
import com.sb3.sbsj.commons.dto.SearchAjaxDto;
import com.sb3.sbsj.commons.exeption.IdNotFoundException;
import com.sb3.sbsj.security.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMybatisMapper iUserMybatisMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public IUser insert(CUDInfoDto cudInfoDto, IUser user) {
        if (!this.isValidInsert(user)) {
            return null;
        }
        UserDto dto = UserDto.builder().build();
        dto.copyFields(user);
        dto.setPassword(encoder.encode(dto.getPassword()));
        cudInfoDto.setCreateInfo(dto);
        this.iUserMybatisMapper.insert(dto);
        return dto;
    }

    private boolean isValidInsert(IUser dto) {
        if (dto == null) {
            return false;
        } else if (dto.getLoginId() == null || dto.getLoginId().isEmpty()) {
            return false;
        } else if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return false;
        } else if (dto.getNickname() == null || dto.getNickname().isEmpty()) {
            return false;
        } else if (dto.getGender() == null || dto.getGender().isEmpty()) {
            return false;
        } else if (dto.getName() == null || dto.getName().isEmpty()) {
            return false;
        } else if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public IUser update(CUDInfoDto cudInfoDto, IUser user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            return null;
        }
        IUser find = this.findById(user.getId());
        find.copyFields(user);
        cudInfoDto.setUpdateInfo(find);
        this.iUserMybatisMapper.update((UserDto) find);
        return find;
    }

    @Override
    public IUser login(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getLoginId() == null || loginRequest.getPassword() == null) {
            return null;
        }
        IUser user = this.iUserMybatisMapper.findByLoginId(loginRequest.getLoginId());
        if (user == null || !encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return null;
        }
        return user;
    }

    @Override
    public Boolean changePassword(IUser user) throws Exception {
        if (user == null) {
            return false;
        }
        UserDto dto = new UserDto();
        dto.copyFields(user);
        dto.setPassword(encoder.encode(dto.getPassword()));
        this.iUserMybatisMapper.changePassword(dto);
        return true;
    }

    @Override
    public IUser findByLoginId(String loginId) {
        if (loginId == null || loginId.isEmpty()) {
            return null;
        }
        UserDto dto = this.iUserMybatisMapper.findByLoginId(loginId);
        return dto;
    }

    @Override
    public IUser findByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return null;
        }
        UserDto dto = this.iUserMybatisMapper.findByEmail(email);
        return dto;
    }

    @Override
    public IUser findByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        UserDto dto = this.iUserMybatisMapper.findByName(name);
        return dto;
    }

    @Override
    public IUser findByNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }
        UserDto find = this.iUserMybatisMapper.findByNickname(nickname);
        return find;
    }

    @Override
    public Integer countAllByNameContains(SearchAjaxDto dto) {
        return this.iUserMybatisMapper.countAllByNameContains(dto);
    }

    @Override
    public List<IUser> findAllByNameContains(SearchAjaxDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();
        List<IUser> result = this.getIUserList(
                this.iUserMybatisMapper.findAllByNameContains(dto)
        );
        return result;
    }

    @Override
    public int idCheck(String loginId) {
        int cnt = iUserMybatisMapper.idCheck(loginId);
        System.out.println("cnt: " + cnt);
        return cnt;
    }

    @Override
    public int emailCheck(String email) {
        int cnt = iUserMybatisMapper.emailCheck(email);
        System.out.println("cnt: " + cnt);
        return cnt;
    }

    @Override
    public int nicknameCheck(String nickname) {
        int cnt = iUserMybatisMapper.nicknameCheck(nickname);
        System.out.println("cnt: " + cnt);
        return cnt;
    }

    private List<IUser> getIUserList(List<UserDto> list) {
        if (list == null || list.size() <= 0) {
            return new ArrayList<>();
        }
        List<IUser> result = list.stream()
                .map(item -> (IUser) item)
                .toList();
        return result;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, IUser user) {
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        this.iUserMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public IUser findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        UserDto dto = this.iUserMybatisMapper.findById(id);
        if (dto == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return dto;
    }
}
