package com.project.community.board.service.impl;

import com.project.community.admin.entity.Category;
import com.project.community.admin.repository.CategoryRepository;
import com.project.community.board.dto.BoardDto;
import com.project.community.board.entity.Board;
import com.project.community.board.mapper.BoardMapper;
import com.project.community.board.model.BoardInput;
import com.project.community.board.model.BoardParam;
import com.project.community.board.repository.BoardRepository;
import com.project.community.board.service.BoardService;
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
public class BoardServiceImpl implements BoardService {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;

	@Override
	public boolean add(BoardInput parameter) {
		// 닉네임 가져오기
		Optional<User> optionalUser = userRepository.findById(parameter.getUserId());
		if (!optionalUser.isPresent()) {
			return false;
		}
		User user = optionalUser.get();
		String nickname = user.getNickname();

		// 카테고리명 가져오기
		Optional<Category> optionalCategory = categoryRepository.findById(parameter.getCategoryId());
		if (!optionalCategory.isPresent()) {
			return false;
		}
		Category category = optionalCategory.get();
		String categoryName = category.getCategoryName();

		Board board = Board.builder()
			.categoryId(parameter.getCategoryId())
			.categoryName(categoryName)
			.subject(parameter.getSubject())
			.contents(parameter.getContents())
			.userId(parameter.getUserId())
			.nickname(nickname)
			.regDt(LocalDateTime.now())
			.build();
		boardRepository.save(board);
		return true;
	}

	@Override
	public List<BoardDto> list(BoardParam parameter) {

		long totalCount = boardMapper.selectListCount(parameter);

		List<BoardDto> list = boardMapper.selectList(parameter);
		if (!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (BoardDto x : list) {
				x.setTotalCount(totalCount);
				x.setSeq(totalCount - parameter.getPageStart() - i);
				i++;
			}
		}
		return list;
	}
}
