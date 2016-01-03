package nu.mulli.builderbuilder;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

import com.thoughtworks.qdox.JavaDocBuilder;

public abstract class AbstractCodeGeneratorMojo extends AbstractMojo {

    private static final String BUILDERBUILDER_STG = "builderbuilder.stg";

    /**
     * @parameter expression="${project}"
     * @required
     * @readonly
     * @since 1.0
     */
    MavenProject project;

    /**
     * Sources
     *
     * @parameter
     * @required
     */
    List<String> sources;

    /**
     * @parameter default-value="target/generated-sources/builderbuilder"
     * @required
     */
    File outputDirectory;

    StringTemplateGroup templates;
    JavaDocBuilder docBuilder;

    @Override
    public void execute() {
        try {
            System.out.println("INFO: Grabbing template : " + BUILDERBUILDER_STG);
            InputStream is = BuilderBuilderMojo.class.getClassLoader().getResourceAsStream(BUILDERBUILDER_STG);
            try {
                this.templates = new StringTemplateGroup(new InputStreamReader(is));
            } finally {
                is.close();
            }

            System.out.println("INFO: Adding sources");
            this.docBuilder = new JavaDocBuilder();
            for (String r : sources) {
                System.out.println("INFO: Adding " + r + " to list of sources");
                docBuilder.addSourceTree(new File(r));
            }

            generate();

            project.addCompileSourceRoot(outputDirectory.getAbsolutePath());

        } catch (Exception e) {
            getLog().error("General error", e);
        }
    }

    protected abstract void generate() throws Exception;

}