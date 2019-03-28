插件下载地址
https://projectlombok.org/setup/eclipse


<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>${lombok.version}</version>
    <scope>provided</scope>
</dependency>

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Data:包含 @ToString、@Getter、@Setter、@EqualsAndHashCode、@NoArgsConstructor

@Slf4j:自动生成log的静态成员变量，代码中可直接用log.info("")
@Builder:构建器模式Person.builder().address("").age(1);
@Cleanup:自动关闭流
@SneakyThrows：自动在方法上增加Throws

val：类型推到，作用不大

@RequiredArgsConstructor：待查

@NotNull
