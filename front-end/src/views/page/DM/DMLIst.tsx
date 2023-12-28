import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import axios from "axios";

type PostDetail = {
    postId: number;
    postUserUUID: string;
    profileImage: string;
    nickName: string;
    content: string;
    createdAt: string;
    postImgs: string[];
  };

const DMList: React.FC = () => {
    const [postDetail, setPostDetail] = useState<PostDetail | null>(null);
    const navigate = useNavigate();
    const location = useLocation(); // useLocation 추가
  
    const { postId } = useParams();

    // const [divs, setDivs] : any = null;
  
    useEffect(() => {
        const getPostDetails = async () => {
            try{
                const { state } = location;
                console.log("Data :", state);

                const users = await axios.get(`/api/users/following-list/${state.postUserUUID}`);
                console.log(users.data);
                const userlist =  users.data.map((item: { nickName: string; })=>item.nickName);
                console.log(userlist);

                const divElements = userlist.map((value : string, index :any) => (
                    <div key={index}>{value}</div>
                  ));
                  
            }catch(e){
                console.error(e);
            }
        
        };

        getPostDetails();

    },[]);

    return(
        <div className="page-body">
        
        </div>
    )
};
export default DMList;