package br.com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job imprimeParImparJob() {

        return jobBuilderFactory
                .get("imprimeParImparJob")
                .start(imprimeParImparStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }

    private Step imprimeParImparStep() {

        return stepBuilderFactory
                .get("imprimeParImparStep")
                .<Integer, String> chunk(10)
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
