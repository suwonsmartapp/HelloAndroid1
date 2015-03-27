
package com.suwonsmartapp.hello.parsing;

/**
 * Created by junsuk on 15. 3. 27..
 */
public class RecipeInfo {
    private String id;
    private String name;
    private String url;

    public RecipeInfo(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("  {");
        builder.append("    id:" + getId() + ",");
        builder.append("    name:" + getName() + ",");
        builder.append("    url:" + getUrl() + "");
        builder.append("  }");

        return builder.toString();
    }
}
