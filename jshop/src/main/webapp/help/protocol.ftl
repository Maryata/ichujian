<#import "/static/common_front.ftl" as html/>
<#import "/indexMenu.ftl" as menu/>
<@html.htmlBase title="注册协议" jqueryValidator=false>

<@menu.menu selectMenu=""/>

<#assign ptitle=systemSetting().title>
<!--登录内容-->
<!--注册内容-->
<div class="content">
  <div class="protocol-box">
    <h1>${ptitle}注册协议</h1>
    <div class="protocol-info">
      <p>亲，继续注册前，请先阅读以下${ptitle}注册协议：</p>
      <p class="stitle">一、${ptitle}使用自己建设的专用电子商务平台系统，通过国际互联网络为用户提供相关产品的分销服务。同时，用户必须是：</p>
      <p>1.必须具备合法经营资格个人或者法人；</p>
      <p>2.自行配备上网的所需设备，包括个人电脑及其他必备上网装置；</p>
      <p>3.自行负担个人上网所支付的与此服务有关的电话费用、网络费用。</p>
      <p class="stitle">二、基于${ptitle}所提供的网络服务的重要性，用户应同意：</p>
      <p>1.提供详尽、正确的个人资料，详尽、正确的用户资料和联系方式；</p>
      <p>2.在个人信息或者公司信息发生变化时，及时变更本站的相关信息，以确保信息的准确性。</p>
      <p class="stitle">三、${ptitle}不公开注册用户的姓名、地址、电子邮箱及个人联系方式等，除以下情况外：</p>
      <p>1.用户授权${ptitle}透露这些信息；</p>
      <p>2.相应的法律及程序要求${ptitle}提供用户的个人资料。如果用户提供的资料包含有不正确的信息，${ptitle}保留结束用户使用网络服务资格的权利。</p>
      <p class="stitle">四、服务条款的修改和服务修订</p>
      <p>${ptitle}有权在必要时修改服务条款，本平台服务条款一旦发生变动，将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的网络服务。如果用户继续享用网络服务，则视为接受服务条款的变动。${ptitle}保留随时修改或中断服务而不需通知用户的权利。${ptitle}行使修改或中断服务的权利，不需对用户或第三方负责。</p>
      <p class="stitle">五、用户隐私制度</p>
      <p>尊重用户个人隐私是${ptitle}的一项基本政策。所以，${ptitle}一定不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在本平台中的非公开内容，除非有法律许可要求或${ptitle}在诚信的基础上认为透露这些信件在以下四种情况是必要的：</p>
      <p>1. 遵守有关法律规定，遵从${ptitle}合法服务程序；</p>
      <p>2. 保持维护${ptitle}的商标所有权；</p>
      <p>3. 在紧急情况下竭力维护用户个人和社会大众的隐私安全；</p>
      <p>4. 符合其他相关的要求。</p>
      <p class="stitle">六、用户的帐号，密码和安全</p>
      <p>用户一旦注册成功，成为${ptitle}的合法用户，将得到一个用户名和密码。用户将对用户名和密码安全负全部责任。由于用户自己管理和使用不当导致的密码被盗用现象，${ptitle}不承担任何责任。另外，每个用户都要对以其用户名进行的所有活动和事件负全责。您可随时根据指示改变您的密码。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告${ptitle}。</p>
      <p class="stitle">七、拒绝提供担保</p>
      <p>用户个人对网络服务的使用承担风险。${ptitle}对此不作任何类型的担保，不论是明确的或隐含的，但是不对商业性的隐含担保、特定目的和不违反规定的适当担保作限制。${ptitle}不担保服务一定能满足用户的要求，也不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保。${ptitle}对在本平台上得到的任何商品购物服务或交易进程，不作担保。</p>
      <p class="stitle">八、有限责任</p>
      <p>${ptitle}对任何直接、间接、偶然及特殊的损害不负责任，这些损害可能来自：不正当使用网络，在网上购买商品或进行同类型服务，在网上进行交易，非法使用网络服务或用户传送的信息有所变动。这些行为都有可能会导致${ptitle}的形象受损，所以${ptitle}事先提出这种损害的可能性。</p>
      <p class="stitle">九、对用户信息的存储和限制</p>
      <p>${ptitle}不对用户所发布信息的删除或储存失败负责。${ptitle}有判定用户的行为是否符合${ptitle}服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，${ptitle}有中断对其提供网络服务的权利。</p>
      <p class="stitle">十、用户必须遵循：</p>
      <p>1. 从中国境内向外传输技术性资料时必须符合中国有关法规；</p>
      <p>2. 使用网络服务不作非法用途；</p>
      <p>3. 不干扰或混乱网络服务；</p>
      <p>4. 遵守所有使用网络服务的网络协议、规定、程序和惯例。 用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输任何教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是禁止的。若用户的行为不符合以上提到的服务条款，${ptitle}将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在${ptitle}散布和传播反动、色情或其他违反国家法律的信息，${ptitle}的系统记录有可能作为用户违反法律的证据。</p>
      <p class="stitle">十一、保障</p>
      <p>用户同意保障和维护${ptitle}全体成员的利益，负责支付由用户使用超出服务范围引起的律师费用，违反服务条款的损害补偿费用等。</p>
      <p class="stitle">十二、结束服务</p>
      <p>用户或${ptitle}可随时根据实际情况中断一项或多项网络服务。${ptitle}不需对任何个人或第三方负责而随时中断服务。用户对后来的条款修改有异议，或对${ptitle}的服务不满，可以行使如下权利：</p>
      <p>1.停止使用${ptitle}网络服务；</p>
      <p>2.通告${ptitle}停止对该用户的服务。结束用户服务后，用户使用网络服务的权利马上中止。从那时起，用户没有权利，${ptitle}也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。</p>
      <p class="stitle">十三、通告</p>
      <p>所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。</p>
      <p class="stitle">十四、网络服务内容的所有权</p>
      <p>${ptitle}定义的网络服务内容包括：文字、软件、声音、图片、录像、图表、广告中的全部内容；电子邮件的全部内容；${ptitle}为用户提供的其他信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能同意${ptitle}和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。${ptitle}所有的文章版权归原文作者和${ptitle}共同所有，任何人需要转载${ptitle}的文章，必须征得原文作者或${ptitle}授权。</p>
      <p class="stitle">十五、权利和义务</p>
      <p>1.加盟用户有义务保证系统的公平性，不准恶意破坏系统的公平性或者攻击系统；</p>
      <p>2.加盟用户有义务维护本系统的形象，不准恶意诽谤系统及系统拥有者：${ptitle}；</p>
      <p>3.系统拥有者将尽最大能力维护系统的稳定性和安全性，保证加盟用户正常使用，天灾、人祸不在此范围内；</p>
      <p>4.做为系统的拥有者${ptitle}，有权力对非法使用本系统的用户进行停用、删除等操作。用户服务条款要与中华人民共和国的法律解释相一致，用户和${ptitle}一致同意服从高等法院所有管辖。如发生${ptitle}服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户产生法律效力和影响。</p>
      <p class="stitle">十六、关于账号的取消规定</p>
      <p>为了保证账号的合法性，凡是自注册成功，账号生效之日起，如有用户非法使用该用户账号或者进行非法操作的，${ptitle}享有封停或删除该账号的权利，并且保留最终的解释权和追究法律责任的权力。</p>
      <p class="m-t30">以上条款的解释权属${ptitle}所有</p>
      <p style="text-align:right;padding-right:50px;">${ptitle}</p>
      <p style="text-align:right;padding-right:50px;">2015年6月</p>
    </div>
  </div>
</div>

</@html.htmlBase>