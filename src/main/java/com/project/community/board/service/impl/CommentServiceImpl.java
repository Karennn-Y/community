package com.project.community.board.service.impl;


import com.project.community.board.dto.CommentDto;
import com.project.community.board.entity.Comment;
import com.project.community.board.mapper.CommentMapper;
import com.project.community.board.model.CommentInput;
import com.project.community.board.model.CommentParam;
import com.project.community.board.repository.CommentRepository;
import com.project.community.board.service.CommentService;
import com.project.community.exception.CustomException;
import com.project.community.exception.ExceptionCode;
import com.project.community.user.entity.User;
import com.project.community.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;

	@Override
	public boolean add(CommentInput parameter) {
		// 닉네임 가져오기
		Optional<User> optionalUser = userRepository.findById(parameter.getUserId());
		if (!optionalUser.isPresent()) {
			throw new CustomException(ExceptionCode.MEMBER_NOT_FOUND);
		}
		User user = optionalUser.get();
		String nickname = user.getNickname();
		// 관리자인지 아닌지 확인
		boolean adminYn = user.isAdminYn();

		Comment comment;
		if (adminYn) {
			comment = Comment.builder()
				.contents(parameter.getContents())
				.nickname("관리자")
				.userId(parameter.getUserId())
				.regDt(LocalDateTime.now())
				.build();
		} else {
			comment = Comment.builder()
				.contents(parameter.getContents())
				.nickname(nickname)
				.userId(parameter.getUserId())
				.boardId(parameter.getBoardId())
				.regDt(LocalDateTime.now())
				.build();
		}

		commentRepository.save(comment);

		return true;
	}

	@Override
	public List<CommentDto> list(CommentParam parameter) {

		long totalCount = commentMapper.selectListCount(parameter);

		List<CommentDto> list = commentMapper.selectList(parameter);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (CommentDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}
		return list;
	}


	@Override
	public boolean del(String idList) {
		if (idList != null && idList.length() > 0) {
			String[] ids = idList.split(",");
			for (String x : ids) {
				long id = 0L;
				try	{
					id = Long.parseLong(x);
				} catch (Exception e) {
				}
				if (id > 0) {
					commentRepository.deleteById(id);
				}
			}
		}
		return true;
	}

}
