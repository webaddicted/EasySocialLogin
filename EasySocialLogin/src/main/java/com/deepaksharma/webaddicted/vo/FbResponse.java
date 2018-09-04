package com.deepaksharma.webaddicted.vo;

import java.util.List;

/**
 * Created by deepaksharma on 30/8/18.
 */

public class FbResponse {


    /**
     * id : 787189058140980
     * name : Deepak Sharma
     * first_name : Deepak
     * last_name : Sharma
     * birthday : 06/04/1995
     * email : sharma9024061407@gmail.com
     * cover : {"offset_x":50,"offset_y":24,"source":"https://platform-lookaside.fbsbx.com/platform/coverpic/?asid=787189058140980&ext=1538660478&hash=AeTTZveCK0ySwKtv"}
     * picture : {"data":{"height":200,"is_silhouette":false,"url":"https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=200&width=200&ext=1538660478&hash=AeSWwvkif1TkFuSJ","width":200}}
     * photos : {"data":[{"created_time":"2016-05-10T07:33:40+0000","id":"531777723682116"},{"created_time":"2018-03-17T02:31:14+0000","name":"Attend job fair at #JECRC_University as a    #company. \n#Now_Time_Changed.","id":"886082998251585"},{"created_time":"2018-02-17T16:02:40+0000","id":"735086106690479"},{"created_time":"2017-10-04T02:16:41+0000","id":"784561885070364"},{"created_time":"2017-07-18T18:36:37+0000","name":"We spent amazing time ...........now i miss that  moment","id":"737494089777144"},{"created_time":"2017-06-19T17:11:21+0000","id":"722217974638089"},{"created_time":"2017-06-07T17:38:22+0000","id":"716256175234269"},{"created_time":"2017-03-23T04:23:47+0000","name":"Here i m....................","id":"674403329419554"},{"created_time":"2016-06-11T20:55:18+0000","id":"543045392555349"},{"created_time":"2016-06-11T20:55:18+0000","id":"543045675888654"},{"created_time":"2016-06-11T13:31:42+0000","id":"542876152572273"},{"created_time":"2016-05-16T11:44:11+0000","id":"533743106818911"},{"created_time":"2016-03-07T13:54:11+0000","id":"506948342831721"},{"created_time":"2015-04-20T08:59:04+0000","id":"384570968402793"},{"created_time":"2015-04-20T08:58:12+0000","id":"384570775069479"},{"created_time":"2015-04-20T08:55:22+0000","id":"384570081736215"},{"created_time":"2015-04-20T08:55:01+0000","id":"384569998402890"},{"created_time":"2015-04-20T08:54:41+0000","id":"384569895069567"},{"created_time":"2015-04-20T08:54:20+0000","id":"384569725069584"},{"created_time":"2015-04-20T08:54:00+0000","id":"384569568402933"}],"paging":{"cursors":{"before":"TlRNeE56YzNOekl6TmpneU1URTJPakUxTWpRNE9UazBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","after":"TXpnME5UWTVOVFk0TkRBeU9UTXpPakUwTWprMU1qQXdORGc2TXprME1EZAzVOalF3TmpRM09ETTIZD"}}}
     */

    private String id;
    private String name;
    private String first_name;
    private String last_name;
    private String birthday;
    private String email;
    private CoverBean cover;
    private PictureBean picture;
    private PhotosBean photos;

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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CoverBean getCover() {
        return cover;
    }

    public void setCover(CoverBean cover) {
        this.cover = cover;
    }

    public PictureBean getPicture() {
        return picture;
    }

    public void setPicture(PictureBean picture) {
        this.picture = picture;
    }

    public PhotosBean getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosBean photos) {
        this.photos = photos;
    }

    public static class CoverBean {
        /**
         * offset_x : 50
         * offset_y : 24
         * source : https://platform-lookaside.fbsbx.com/platform/coverpic/?asid=787189058140980&ext=1538660478&hash=AeTTZveCK0ySwKtv
         */

        private int offset_x;
        private int offset_y;
        private String source;

        public int getOffset_x() {
            return offset_x;
        }

        public void setOffset_x(int offset_x) {
            this.offset_x = offset_x;
        }

        public int getOffset_y() {
            return offset_y;
        }

        public void setOffset_y(int offset_y) {
            this.offset_y = offset_y;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public static class PictureBean {
        /**
         * data : {"height":200,"is_silhouette":false,"url":"https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=200&width=200&ext=1538660478&hash=AeSWwvkif1TkFuSJ","width":200}
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
             * height : 200
             * is_silhouette : false
             * url : https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=787189058140980&height=200&width=200&ext=1538660478&hash=AeSWwvkif1TkFuSJ
             * width : 200
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

    public static class PhotosBean {
        /**
         * data : [{"created_time":"2016-05-10T07:33:40+0000","id":"531777723682116"},{"created_time":"2018-03-17T02:31:14+0000","name":"Attend job fair at #JECRC_University as a    #company. \n#Now_Time_Changed.","id":"886082998251585"},{"created_time":"2018-02-17T16:02:40+0000","id":"735086106690479"},{"created_time":"2017-10-04T02:16:41+0000","id":"784561885070364"},{"created_time":"2017-07-18T18:36:37+0000","name":"We spent amazing time ...........now i miss that  moment","id":"737494089777144"},{"created_time":"2017-06-19T17:11:21+0000","id":"722217974638089"},{"created_time":"2017-06-07T17:38:22+0000","id":"716256175234269"},{"created_time":"2017-03-23T04:23:47+0000","name":"Here i m....................","id":"674403329419554"},{"created_time":"2016-06-11T20:55:18+0000","id":"543045392555349"},{"created_time":"2016-06-11T20:55:18+0000","id":"543045675888654"},{"created_time":"2016-06-11T13:31:42+0000","id":"542876152572273"},{"created_time":"2016-05-16T11:44:11+0000","id":"533743106818911"},{"created_time":"2016-03-07T13:54:11+0000","id":"506948342831721"},{"created_time":"2015-04-20T08:59:04+0000","id":"384570968402793"},{"created_time":"2015-04-20T08:58:12+0000","id":"384570775069479"},{"created_time":"2015-04-20T08:55:22+0000","id":"384570081736215"},{"created_time":"2015-04-20T08:55:01+0000","id":"384569998402890"},{"created_time":"2015-04-20T08:54:41+0000","id":"384569895069567"},{"created_time":"2015-04-20T08:54:20+0000","id":"384569725069584"},{"created_time":"2015-04-20T08:54:00+0000","id":"384569568402933"}]
         * paging : {"cursors":{"before":"TlRNeE56YzNOekl6TmpneU1URTJPakUxTWpRNE9UazBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","after":"TXpnME5UWTVOVFk0TkRBeU9UTXpPakUwTWprMU1qQXdORGc2TXprME1EZAzVOalF3TmpRM09ETTIZD"}}
         */

        private PagingBean paging;
        private List<DataBeanX> data;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<DataBeanX> getData() {
            return data;
        }

        public void setData(List<DataBeanX> data) {
            this.data = data;
        }

        public static class PagingBean {
            /**
             * cursors : {"before":"TlRNeE56YzNOekl6TmpneU1URTJPakUxTWpRNE9UazBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD","after":"TXpnME5UWTVOVFk0TkRBeU9UTXpPakUwTWprMU1qQXdORGc2TXprME1EZAzVOalF3TmpRM09ETTIZD"}
             */

            private CursorsBean cursors;

            public CursorsBean getCursors() {
                return cursors;
            }

            public void setCursors(CursorsBean cursors) {
                this.cursors = cursors;
            }

            public static class CursorsBean {
                /**
                 * before : TlRNeE56YzNOekl6TmpneU1URTJPakUxTWpRNE9UazBNREk2TXprME1EZAzVOalF3TmpRM09ETTIZD
                 * after : TXpnME5UWTVOVFk0TkRBeU9UTXpPakUwTWprMU1qQXdORGc2TXprME1EZAzVOalF3TmpRM09ETTIZD
                 */

                private String before;
                private String after;

                public String getBefore() {
                    return before;
                }

                public void setBefore(String before) {
                    this.before = before;
                }

                public String getAfter() {
                    return after;
                }

                public void setAfter(String after) {
                    this.after = after;
                }
            }
        }

        public static class DataBeanX {
            /**
             * created_time : 2016-05-10T07:33:40+0000
             * id : 531777723682116
             * name : Attend job fair at #JECRC_University as a    #company.
             #Now_Time_Changed.
             */

            private String created_time;
            private String id;
            private String name;

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
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
        }
    }
}
