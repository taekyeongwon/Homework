package com.example.tkw33.homework3modify.DB;

import com.example.tkw33.homework3modify.GeoJson.LinkLatLng;
import com.example.tkw33.homework3modify.GeoJson.NodeLatLng;

import java.util.ArrayList;

public class MakeGraph {
    int nodeSize, linkSize;
    double nodeLat, nodeLng, linkTailLat, linkTailLng, linkHeadLat, linkHeadLng;
    NodeLatLng nodeLatLng;
    LinkLatLng linkLatLng;
    ArrayList<LinkLatLng> mList;
    public MakeGraph(){
        //nodeLatLng = new NodeLatLng();
        init();
    }
    public void init(){
        //nodeSize = NodeLatLng.nlist.size();
        //linkSize = LinkLatLng.llist.size();
        for(int i = 0; i < NodeLatLng.nlist.size() - 1; i++){
            mList = new ArrayList<>();
            nodeLat = NodeLatLng.nlist.get(i).lat;
            nodeLng = NodeLatLng.nlist.get(i).lng;
            for(int j = 0; j < LinkLatLng.llist.size() - 1; j++){
                linkTailLat = LinkLatLng.llist.get(j).coordinate.get(0).latitude;    //index 0이 tail이 맞는지.
                linkTailLng = LinkLatLng.llist.get(j).coordinate.get(0).longitude;
                linkHeadLat = LinkLatLng.llist.get(j).coordinate.get(LinkLatLng.llist.get(j).coordinate.size() - 1).latitude;
                linkHeadLng = LinkLatLng.llist.get(j).coordinate.get(LinkLatLng.llist.get(j).coordinate.size() - 1).longitude;
                //범위 수정하고 a*알고리즘 수행할수있도록.
                if( ((nodeLat-0.0015 <= linkTailLat &&  linkTailLat <= nodeLat+0.0015) &&
                        (nodeLng-0.0015 <= linkTailLng && linkTailLng <= nodeLng+0.0015)) &&
                        ((nodeLat-0.0015 <= linkHeadLat && linkHeadLat <= nodeLat+0.0015) &&
                                (nodeLng-0.0015 <= linkHeadLng && linkHeadLng <= nodeLng+0.0015)) ){  //또는 nodeLat <= linkTailLat <= nodeLat+0.0001 같은 방식으로
                    linkLatLng = new LinkLatLng(LinkLatLng.llist.get(j).link_id, LinkLatLng.llist.get(j).coordinate
                    , LinkLatLng.llist.get(j).distance);
                    //nodeLatLng.setLink(linkLatLng);
                    mList.add(linkLatLng);
                    //NodeLatLng.nlist.get(i).setLink(linkLatLng);
                }
                /*else if((nodeLat-0.0015 <= linkHeadLat && linkHeadLat <= nodeLat+0.0015) &&
                        (nodeLng-0.0015 <= linkHeadLng && linkHeadLng <= nodeLng+0.0015)) {
                    linkLatLng = new LinkLatLng(LinkLatLng.llist.get(j).link_id, LinkLatLng.llist.get(j).coordinate
                    , LinkLatLng.llist.get(j).distance);
                    //nodeLatLng.setLink(linkLatLng);
                    mList.add(linkLatLng);
                    //NodeLatLng.nlist.get(i).setLink(linkLatLng);
                }*/
            }
            NodeLatLng.nlist.get(i).setLink(mList);
        }
    }

}
