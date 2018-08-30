package com.deepaksharma.webaddicted.vo;

/**
 * Created by deepaksharma on 30/8/18.
 */

public class FbResponse {

    /**
     * id : 787189058140980
     * name : Deepak Sharma
     * first_name : Deepak
     * last_name : Sharma
     * email : sharma9024061407@gmail.com
     * birthday : 06/04/1995
     * picture : {"data":{"height":50,"is_silhouette":false,"url":"https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=50&width=50&ext=1538207070&hash=AeTXW5ANh40drgsS","width":50}}
     */

    private String id;
    private String name;
    private String first_name;
    private String last_name;
    private String email;
    private String birthday;
    private PictureBean picture;

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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public PictureBean getPicture() {
        return picture;
    }

    public void setPicture(PictureBean picture) {
        this.picture = picture;
    }

    public static class PictureBean {
        /**
         * data : {"height":50,"is_silhouette":false,"url":"https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=50&width=50&ext=1538207070&hash=AeTXW5ANh40drgsS","width":50}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * height : 50
             * is_silhouette : false
             * url : https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=50&width=50&ext=1538207070&hash=AeTXW5ANh40drgsS
             * width : 50
             */

            private int height;
            private boolean is_silhouette;
            private String url;
            private int width;

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public boolean isIs_silhouette() {
                return is_silhouette;
            }

            public void setIs_silhouette(boolean is_silhouette) {
                this.is_silhouette = is_silhouette;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }
        }
    }
}
