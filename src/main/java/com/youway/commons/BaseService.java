package com.youway.commons;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.youway.commons.utils.PageUtils;
import com.youway.commons.utils.SearchFilter;
import com.youway.commons.utils.SearchFilter.Operator;
import com.youway.commons.utils.UserUtils;

@Service
public abstract class BaseService<T, ID extends Serializable> {

	@Autowired
	protected JpaDao<T, ID> japDao;

	public Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

	public void setJpaDao(JpaDao<T, ID> japDao) {
		this.japDao = japDao;
	}

	/**
	 * 按照主键查询
	 * 
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	@Transactional(readOnly = true)
	public Optional<T> findOne(ID id) {
		return japDao.findById(id);
	}

	/**
	 * 保存单个实体
	 * 
	 * @param t
	 *            实体
	 * @return 返回保存的实体
	 */
	public T save(T t) {
		UserUtils.stamp(t);
		return japDao.save(t);
	}

	/**
	 * 保存多个实体
	 * 
	 * @param List<t>
	 *            实体
	 * @return 返回保存的实体
	 */
	public List<T> save(List<T> tlist) {
		tlist.forEach(t -> {
			UserUtils.stamp(t);
		});
		return japDao.saveAll(tlist);
	}

	/**
	 * 根据主键删除相应实体
	 * 
	 * @param id
	 *            主键
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void delete(ID id) throws IllegalAccessException, InvocationTargetException {
		T t = findOne(id).get();
		BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
		save(t);
	}

	/**
	 * 删除实体
	 * 
	 * @param t
	 *            实体
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public void delete(T t) throws IllegalAccessException, InvocationTargetException {
		BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
		save(t);
	}

	/**
	 * 删除实体list
	 * 
	 * @param t
	 *            实体
	 */
	public void delete(List<T> t) {
		t.forEach(a -> {
			try {
				BeanUtils.setProperty(t, "isActive", Boolean.FALSE);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		save(t);
	}

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	@Transactional(readOnly = true)
	public long count() {
		return japDao.count();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return japDao.findAll();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Map<String, Object> searchParams, Direction direction, String[] sortType) {
		return japDao.findAll(PageUtils.buildSpec(searchParams), Sort.by(direction, sortType));
	}

	/**
	 * 获取分页
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<T> getPage(Map<String, Object> searchParams, int pageNumber, int pageSize, Direction direction,
			String... sortType) {

		return japDao.findAll(PageUtils.buildSpec(searchParams),
				PageRequest.of(pageNumber - 1, pageSize, Sort.by(direction, sortType)));
	}

	/**
	 * 根据某个字段查询实体集合
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findByParam(String param, Operator operator, Object object) {
		return japDao
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 根据某个字段查询实体集合(排序)
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findByParam(String param, Operator operator, Object object, Sort sort) {
		return japDao
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))), sort);
	}

	/**
	 * 根据某个字段查询单个实体
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public T getByParam(String param, Operator operator, Object object) {
		List<T> list = japDao
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
		return list.isEmpty() ? null : list.get(0);
	}

	/**
	 * 根据某个字段查询数量
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public long countByParam(String param, Operator operator, Object object) {
		return japDao.count(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
				new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 根据查询条件获取所有
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findAll(Specification<T> spec) {
		return japDao.findAll(spec);
	}

	/**
	 * 根据查询条件获取
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public Optional<T> findOne(Specification<T> spec) {
		return (Optional<T>) japDao.findOne(spec);
	}

	/**
	 * 根据查询条件获取数量
	 * 
	 * @param spec
	 * @return
	 */
	@Transactional(readOnly = true)
	public long count(Specification<T> spec) {
		return japDao.count(spec);
	}

	/**
	 * 根据某个字段查询实体集合(所有状态下)
	 * 
	 * @param param
	 * @param operator
	 * @param object
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<T> findALLByParam(String param, Operator operator, Object object) {
		return japDao
				.findAll(PageUtils.bySearchFilter(ImmutableList.of(new SearchFilter(param, operator, object),
						new SearchFilter("isActive", Operator.EQ, Boolean.TRUE))));
	}

	/**
	 * 克隆对象属性值
	 */
	@Transactional(readOnly = true)
	public void mapper(Object source, Object destination) {
		mapper.map(source, destination);
	}

	/**
	 * 获取集合的子列表，第二个参数是获取的大小，从0开始
	 */
	@Transactional(readOnly = true)
	public List<T> getLimit(List<T> list, Integer limit) {
		return list.size() > limit ? list.subList(0, limit) : list;
	}

}
