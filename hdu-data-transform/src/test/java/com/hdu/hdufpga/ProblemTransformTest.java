package com.hdu.hdufpga;

import com.hdu.hdufpga.entity.NewProblem;
import com.hdu.hdufpga.entity.OldProblem;
import com.hdu.hdufpga.service.NewProblemService;
import com.hdu.hdufpga.service.OldProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ProblemTransformTest {
    @Resource
    private NewProblemService newProblemService;

    @Resource
    private OldProblemService oldProblemService;

    @Test
    void transform() {
        List<OldProblem> oldProblemList = oldProblemService.list();
        oldProblemList.forEach(e -> {
            NewProblem newProblem = new NewProblem();
            newProblem.setId(e.getProblemId());
            newProblem.setType(e.getType());
            newProblem.setContent(e.getContent());
            newProblem.setChoice(e.getChoice());
            newProblem.setAnswer(e.getAnswer());
            newProblem.setCreateTime(new Date());
            newProblem.setUpdateTime(new Date());
            newProblemService.save(newProblem);
        });
    }
}
