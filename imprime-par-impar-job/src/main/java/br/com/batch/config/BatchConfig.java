package br.com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job imprimeParImparJob() {

        return new JobBuilder("imprimeParImparJob", jobRepository)
                .start(imprimeParImparStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Step imprimeParImparStep() {

        return new StepBuilder("imprimeParImparStep", jobRepository)
                .<Integer, String> chunk(10, transactionManager)
                .reader(contaAteDezReader())
                .processor(parOuImparProcessor())
                .writer(imprimeWriter())
                .build();
    }

    private IteratorItemReader<Integer> contaAteDezReader() {

        List<Integer> numerosDeUmAteDez = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        return new IteratorItemReader<>(numerosDeUmAteDez.iterator());
    }

    private FunctionItemProcessor<Integer, String> parOuImparProcessor() {

        return new FunctionItemProcessor<>
                (item -> item % 2 == 0 ? mensagem(item, "Par") : mensagem(item, "Impar"));
    }

    private ItemWriter<String> imprimeWriter() {

        return itens -> itens.forEach(System.out::println);
    }

    private static String mensagem(Integer item, String parOuImpar) {

        return String.format("Item %s é %s", item, parOuImpar);
    }
}
