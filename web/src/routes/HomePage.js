/**
 * Created by admin on 2017/5/17.
 */
import React from 'react';
import {connect} from 'dva';
import {FormattedMessage, FormattedDate} from 'react-intl';

const HomePage = ({common}) => {
  const userName = (<b>{common.userName}</b>);
  return (
    <div>
      <FormattedMessage
        id="home.welcome"
        values={
          {
            userName: userName
          }
        }
      />
      <FormattedDate
        value={Date.now()}
      />
    </div>
  )
} ;

const mapStateToProps = state => state;

export default connect(mapStateToProps)(HomePage);
