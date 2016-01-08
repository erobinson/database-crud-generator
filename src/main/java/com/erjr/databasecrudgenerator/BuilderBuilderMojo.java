package com.erjr.databasecrudgenerator;

import java.util.Iterator;

import org.antlr.stringtemplate.StringTemplate;

/**
 * @goal generate-sources
 * @phase generate-sources
 */
public class BuilderBuilderMojo extends AbstractCodeGeneratorMojo {

    @Override
    public void generate() throws Exception {
        DatabaseStructure databaseStructure = new DatabaseStructure();
        databaseStructure.queryDatabaseForStructure();
        String packageToWriteTo = "com.erjr";
        TemplateGenerator templateGenerator = new TemplateGenerator(packageToWriteTo , outputDirectory);
        Iterator<?> templateIterator = templates.getTemplateNames().iterator();
        while (templateIterator.hasNext()) {
            String templateName = (String) templateIterator.next();
            StringTemplate template = templates.getInstanceOf(templateName);
            templateGenerator.buildTemplateFromDatabaseStructure(template, databaseStructure);
        }
    }
//
//    public void generateBuilderFor(JavaClass javaClass) throws IOException {
//        System.out.println("INFO: Generating for java class : " + javaClass);
//        for (JavaMethod method : javaClass.getMethods()) {
//            if (method.isConstructor()) {
//                DocletTag docletTag = method.getTagByName("builder");
//                DocletTag docletTagCRUD = method.getTagByName("crud");
//                if (docletTag != null) {
//                    generateBuilderForMethod(javaClass, method, docletTag);
//                } else if (docletTagCRUD != null) {
//                    generateBuilderForCRUD(javaClass, method, docletTagCRUD);
//                }
//            }
//        }
//    }
//
//    private void generateBuilderForCRUD(JavaClass javaClass, JavaMethod method, DocletTag docletTagCRUD) throws IOException {
//        StringTemplate stringTemplate = templates.getInstanceOf("crudBuilder");
//        
//        String packageName = docletTagCRUD.getNamedParameter("package");
//        if (packageName == null)
//            packageName = javaClass.getPackageName();
//        stringTemplate.setAttribute("packageName", packageName);
//        
//        stringTemplate.setAttribute("className", javaClass.getName());
//        
//        List<Param> ps = new LinkedList<Param>();
//        for (JavaParameter p : method.getParameters()) {
//            Param param = new Param(p.getType().toGenericString(), p.getName());
//            if (!p.getName().startsWith("_"))
//                ps.add(param);
//        }
//        stringTemplate.setAttribute("elements", ps);
//        writeTemplateToJavaCode(javaClass.getName()+"Base", packageName, stringTemplate);
//    }
//
//    private void generateBuilderForMethod(JavaClass javaClass, JavaMethod method, DocletTag docletTag)
//            throws IOException {
//        boolean builderAbstract = Boolean.valueOf(docletTag.getNamedParameter("abstract"));
//        String builderName = docletTag.getNamedParameter("name");
//
//        if (builderName == null) {
//            if (builderAbstract)
//                builderName = "Abstract" + javaClass.getName() + "Builder";
//            else
//                builderName = javaClass.getName() + "Builder";
//        }
//
//        String createMethod = docletTag.getNamedParameter("createMethod");
//        if (createMethod == null)
//            createMethod = "create";
//
//        String packageName = docletTag.getNamedParameter("package");
//        if (packageName == null)
//            packageName = javaClass.getPackageName();
//
//        String extendsClass = docletTag.getNamedParameter("extends");
//
//        StringTemplate stringTemplate = templates.getInstanceOf(builderAbstract ? "abstractBuilder" : "builder");
//
//        stringTemplate.setAttribute("packageName", packageName);
//        stringTemplate.setAttribute("builderName", builderName);
//        stringTemplate.setAttribute("resultClass", javaClass.asType().toString());
//        stringTemplate.setAttribute("createMethod", createMethod);
//        stringTemplate.setAttribute("extendsClass", extendsClass);
//
//        List<Param> ps = new LinkedList<Param>();
//        List<Param> cs = new LinkedList<Param>();
//        for (JavaParameter p : method.getParameters()) {
//            Param param = new Param(p.getType().toGenericString(), p.getName());
//            if (!p.getName().startsWith("_"))
//                ps.add(param);
//            cs.add(param);
//        }
//        stringTemplate.setAttribute("parameters", ps);
//        stringTemplate.setAttribute("arguments", cs);
//
//        writeTemplateToJavaCode(builderName, packageName, stringTemplate);
//    }
//
//    private void writeTemplateToJavaCode(String builderName, String packageName, StringTemplate stringTemplate)
//            throws IOException {
//        File pd = new File(outputDirectory, packageName.replaceAll("\\.", "/"));
//        pd.mkdirs();
//
//        FileWriter out = new FileWriter(new File(pd, builderName + ".java"));
//        System.out.println("INFO: writing to "+builderName+".java");
//        try {
//            out.append(stringTemplate.toString());
//        } finally {
//            out.flush();
//            out.close();
//        }
//
//        if (getLog().isDebugEnabled()) {
//            getLog().debug(builderName + ".java :");
//            getLog().debug(stringTemplate.toString());
//        }
//    }
//
//    public static class Param {
//        public final String type;
//        public final String name;
//
//        public Param(String type, String name) {
//            this.type = type;
//            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
//            this.name = name;
//        }
//    }
}
