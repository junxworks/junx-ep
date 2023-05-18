package io.github.junxworks.ep.qlexp.functions;

/**
 * 函数定义对象
 *      
 * @param name 函数名称
 * @param aClassName 类名称
 * @param aFunctionName 类中的方法名称
 * @param aParameterClassTypes 方法的参数类型名称
 * @param aParameterDesc 方法的参数说明
 * @param aParameterAnnotation 方法的参数注解
 * @param errorInfo 如果函数执行的结果是false，需要输出的错误信息
 */
public class ClassFunctionDefinition {
	private String name;

	private String aClassName;

	private String aFunctionName;

	private Class<?>[] aParameterClassTypes;

	private String[] aParameterDesc;

	private String[] aParameterAnnotation;

	private String errorInfo;

	public ClassFunctionDefinition(String name, String aClassName, String aFunctionName, Class<?>[] aParameterClassTypes, String[] aParameterDesc, String[] aParameterAnnotation, String errorInfo) {
		super();
		this.name = name;
		this.aClassName = aClassName;
		this.aFunctionName = aFunctionName;
		this.aParameterClassTypes = aParameterClassTypes;
		this.aParameterDesc = aParameterDesc;
		this.aParameterAnnotation = aParameterAnnotation;
		this.errorInfo = errorInfo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getaClassName() {
		return aClassName;
	}

	public void setaClassName(String aClassName) {
		this.aClassName = aClassName;
	}

	public String getaFunctionName() {
		return aFunctionName;
	}

	public void setaFunctionName(String aFunctionName) {
		this.aFunctionName = aFunctionName;
	}

	public Class<?>[] getaParameterClassTypes() {
		return aParameterClassTypes;
	}

	public void setaParameterClassTypes(Class<?>[] aParameterClassTypes) {
		this.aParameterClassTypes = aParameterClassTypes;
	}

	public String[] getaParameterDesc() {
		return aParameterDesc;
	}

	public void setaParameterDesc(String[] aParameterDesc) {
		this.aParameterDesc = aParameterDesc;
	}

	public String[] getaParameterAnnotation() {
		return aParameterAnnotation;
	}

	public void setaParameterAnnotation(String[] aParameterAnnotation) {
		this.aParameterAnnotation = aParameterAnnotation;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
