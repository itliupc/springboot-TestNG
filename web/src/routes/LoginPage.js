/**
 * Created by Administrator on 2017/8/14 0014.
 */
import React from 'react';
import {Icon, Form, Input, Button, message} from 'antd';
import style from './login-page.less';
import {routerRedux} from 'dva/router';
import {connect} from 'dva';
import {FormattedMessage, injectIntl} from 'react-intl';

const FormItem = Form.Item;

const LoginPage = ({form, dispatch, intl}) => {
    const {getFieldDecorator, validateFields} = form;
    return (
        <div className={style.loginbg}>
            <div className={style.wrapper}>
                <div className={style.body}>
                    <header className={style.header}>
                      <FormattedMessage id="login.userLogin" />
                    </header>
                    <section className={style.form}>
                        <Form onSubmit={(e)=>{
                            e.preventDefault();
                            validateFields((err, values)=>{
                                dispatch({type:'common/login', payload:values});
                            });
                        }}>
                        <FormItem
                          label={intl.formatMessage({id: "login.account"})}
                          labelCol={{span: 6}}
                          wrapperCol={{span: 14}}>
                            {getFieldDecorator('account', {
                                rules: [
                                    {
                                        required: true,
                                        message: intl.formatMessage({id: "login.warn.emptyAccount"}),
                                        type: 'string',
                                    },
                                ],
                            })(
                                <Input type="text" placeholder="account" addonBefore={<Icon type="user"/>}/>,
                            )}
                        </FormItem>
                        <FormItem
                          label={intl.formatMessage({id: "login.password"})}
                          labelCol={{span: 6}}
                          wrapperCol={{span: 14}}>
                            {getFieldDecorator('password', {
                                rules: [
                                    {
                                        required: true,
                                        message: intl.formatMessage({id: "login.warn.emptyPassword"}),
                                        type: 'string',
                                    },
                                ],
                            })(
                                <Input type="password" placeholder="password" addonBefore={<Icon type="lock"/>}/>,
                            )}
                        </FormItem>
                        <Button  className={style.btn} type="primary" htmlType="submit">
                          <FormattedMessage id="login.signIn" />
                        </Button>
                    </Form>
                    </section>
                </div>
            </div>
        </div>
    );

};
const mapStateToProps = state => ({i18n: state.i18n});

export default connect(mapStateToProps)(Form.create()(injectIntl(LoginPage)));
