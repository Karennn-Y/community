package com.project.community.board.model;

import com.project.community.common.model.CommonParam;
import lombok.Data;

@Data
public class CommentParam extends CommonParam {
	long id;
	long boardId;
	String userId;

}
